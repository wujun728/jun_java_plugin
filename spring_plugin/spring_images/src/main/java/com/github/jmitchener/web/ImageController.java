/*
 * Copyright (c) 2011 Jim Mitchener <jcm@packetpan.org>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.github.jmitchener.web;

import java.io.IOException;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import com.github.jmitchener.model.Image;
import com.github.jmitchener.service.ImageService;

/**
 * Handles displaying and uploading of images.
 */
@Controller
@RequestMapping("/images")
public class ImageController {

    @Inject
    private ImageService imageService;

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);
    
    public ImageController() {}
    
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getRecent(Model model) {
        logger.info("GET /images");
        
        model.addAttribute("recentImages", imageService.getRecent(10));
        return "/images/index";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String uploadForm(Locale locale, Model model) {
        logger.info("GET /images/upload");

        return "/images/upload";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getInfo(@PathVariable Long id,
                          Model model)
    {
        logger.info("GET /images/{}", id);
        
        Image img = imageService.find(id);

        if (img == null)
            throw new ResourceNotFoundException();

        model.addAttribute("image", img);

        return "/images/show";
    }

    @RequestMapping(value = "/{id}/raw", method = RequestMethod.GET)
    public void getImage(@PathVariable Long id,
                         HttpServletResponse resp)
    {
        logger.info("GET /images/{}/raw", id);
        
        Image img = imageService.find(id);

        if (img == null)
            throw new ResourceNotFoundException();

        try {
            resp.setContentType(img.getContentType());
            resp.getOutputStream().write(img.getData());
        } catch (IOException e) {
            logger.error("Couldn't write image to OutputStream", e);
        }
    }

    @RequestMapping(value = "/{id}/thumbnail", method = RequestMethod.GET)
    public void getThumbnail(@PathVariable Long id, HttpServletResponse resp)
        throws IOException
    {
        logger.info("GET /images/{}/thumbnail", id);
        
        Image img = imageService.find(id);

        if (img == null)
            throw new ResourceNotFoundException();

        resp.setContentType(img.getContentType());
        resp.getOutputStream().write(img.getThumbnail());
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile files[],
                         Model model)
    {
        logger.info("POST /images/upload");

        try {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {

                    Image img = new Image();

                    img.setData(file.getBytes());
                    img.setContentType(file.getContentType());

                    imageService.save(img);
                }
            }

            return "redirect:/images";
        } catch (MaxUploadSizeExceededException e) {
            logger.debug("Max upload size exceeded!");
        } catch (IOException e) {
            logger.error("Failed to read uploaded image", e);

            throw new RuntimeException("Could not process upload. Please try again.");
        }

        return "/images/uploadFailure";
    }
}
