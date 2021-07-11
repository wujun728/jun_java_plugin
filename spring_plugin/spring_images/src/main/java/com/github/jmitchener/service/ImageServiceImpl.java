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
package com.github.jmitchener.service;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.jmitchener.model.Image;
import com.github.jmitchener.repository.ImageRepository;

@Service
public class ImageServiceImpl implements ImageService {
    
    private static final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

    @Inject
    private ImageRepository repo;
    
    public ImageServiceImpl() {}
    
    public ImageServiceImpl(ImageRepository repo) {
        this.repo = repo;
    }

    public Image find(Long id) {
        return repo.find(id);
    }

    @Transactional
    public void save(Image image) {

        image.setDate(new Date());
        
        try {
            image.setThumbnail(generateThumbnail(image.getData()));
        } catch (IOException e) {
            logger.error("Failed to generate thumbnail", e);
            throw new RuntimeException("Failed to generate thumbnail");
        }

        repo.save(image);
    }

    public List<Image> getRecent(int count) {
        return repo.getRecent(count);
    }

    /**
     * Create a thumbnail for the given image
     *
     * @param image Image to generate a thumbnail of.
     * 
     * @throws IOException If there is a problem parsing the image or writing the thumbnail.
     *
     * @return The thumbnail
     */
    private static byte[] generateThumbnail(byte[] image)
        throws IOException
    {
        /*
         * FIXME - this should probably be located elsewhere.
         */
        BufferedImage original = ImageIO.read(new ByteArrayInputStream(image));

        BufferedImage resize = new BufferedImage(100, 100, original.getType());

        Graphics2D g = resize.createGraphics();

        g.drawImage(original, 0, 0, 100, 100, null);
        g.dispose();

        ByteArrayOutputStream out = new ByteArrayOutputStream(1000);

        // FIXME - don't force jpg for thumbnails
        ImageIO.write(resize, "jpg", out);

        return out.toByteArray();
    }
}
