package com.portal.controller;

import com.portal.exception.ImageUploadException;
import com.portal.model.Blogger;
import com.portal.service.BloggerService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by andrii on 4/26/14.
 */
@Controller
public class RegistrationController {

    @Autowired
    BloggerService bloggerService;

    @RequestMapping(value="loggedout", method= RequestMethod.GET)
    public String logout() {
        return "loggedout";

    }

    @RequestMapping(method = RequestMethod.GET, value = "signup")
    public String createNewBloggerProfile(Model model) {
        Blogger blogger = new Blogger();
        model.addAttribute("blogger", blogger);
        return "signup";
    }

    @RequestMapping(method = RequestMethod.POST, value = "signup")
    public String addNewBloggerFromForm(@Valid Blogger blogger,
                                        BindingResult bindingResult,
                                        @RequestParam(value = "image", required = false) MultipartFile image, HttpServletRequest request) throws UnsupportedEncodingException {
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        bloggerService.saveBlogger(blogger);
        try {
            if (!image.isEmpty()) {
                validateImage(image);
                saveImage("nana.jpg", image, request);
            }
        } catch (Exception e) {
            bindingResult.reject(e.getMessage());
            return "signup";
        }

        return "redirect:/posts/blogger/" + blogger.getEmail();
    }

    @RequestMapping(value = "signin", method = RequestMethod.GET)
    public String signIn(Model model) {
        Blogger blogger = new Blogger();
        model.addAttribute(blogger);
        return "signin";
    }

    @RequestMapping(value = "signin", method = RequestMethod.POST)
    public String signInPost(@RequestParam(value = "email", required = true) String email,
                             @RequestParam(value = "password", required = true) String password, Model model,
                             HttpServletRequest request) {
        Blogger blogger = null;
        try {
            blogger = bloggerService.getBloggerByEmail(email);
        } catch (NoResultException e) {
            model.addAttribute("errorMessage", "Sorry, there is no such user registered");
            return "signin";
        }
        //model.addAttribute(blogger);
        //org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder encoder = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
        //log.debug("admin@i.ua password 11 = " + encoder.encode("11"));
        return "redirect:/posts/blogger/" + blogger.getLogin();
    }

    private void validateImage(MultipartFile image) {
        if (!image.getContentType().equals("image/jpeg")) {
            throw new ImageUploadException("Only JPG images accepted");
        }

    }

    private void saveImage(String fileName, MultipartFile image,
                           HttpServletRequest request) {

        //TODO Save avatar to particular image store but no at dynamic application context
        File file = new File(request.getRealPath("/") + "/resources/store/"
                + fileName);
        try {
            FileUtils.writeByteArrayToFile(file, image.getBytes());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new ImageUploadException("Unable to save image", e);
        }

    }
}