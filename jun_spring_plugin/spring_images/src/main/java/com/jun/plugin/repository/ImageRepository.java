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
package com.jun.plugin.repository;

import java.util.Collection;
import java.util.List;

import com.jun.plugin.model.Image;

public interface ImageRepository {

    /**
     * Find an image by its id.
     */
    public Image find(long id);
    
    /**
     * Find all images.
     */
    public List<Image> findAll();

    /**
     * Save a new image or update an existing one.
     * 
     * @param img Image to save/update
     * 
     * @return The persisted image.
     */
    public Image save(Image img);
    
    /**
     * Save or update a collection of images.
     * 
     * @param images Collection of Images to save or update.
     */
    public void save(Collection<Image> images);
    
    /**
     * Delete an image from the database.
     * 
     * @param image Image to be deleted
     */
    public void delete(Image image);

    /**
     * Get a list of the most recent images.
     *
     * @param count The number of images to return.
     *
     * @return A list of recent images.
     */
    public List<Image> getRecent(int count);

}