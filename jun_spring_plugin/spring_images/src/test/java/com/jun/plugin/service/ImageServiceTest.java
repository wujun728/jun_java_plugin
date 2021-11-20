/*
 * Copyright (c) 2011 Jim C. Mitchener <jcm@packetpan.org>
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
package com.jun.plugin.service;

import static com.jun.plugin.util.ImageTestUtil.loadSamplePNG;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.jun.plugin.model.Image;
import com.jun.plugin.repository.ImageRepository;
import com.jun.plugin.service.ImageService;
import com.jun.plugin.service.ImageServiceImpl;

public class ImageServiceTest {
    
    private ImageService service;
    private ImageRepository repo;
    
    @Before
    public void setUp() {
        repo = mock(ImageRepository.class);
        service = new ImageServiceImpl(repo);
    }

    @Test
    public void testFindImageById() {
        Image img = new Image();
        
        when(repo.find(1L)).thenReturn(img);
        
        Image ret = service.find(1L);
        
        verify(repo).find(1L);
        
        assertEquals(img, ret);
    }
    
    @Test
    public void testFindRecent() {
        List<Image> images = new ArrayList<Image>();
        
        for (int i = 0; i < 5; i++)
            images.add(new Image());
        
        when(repo.getRecent(5)).thenReturn(images);
        
        List<Image> ret = service.getRecent(5);
        
        verify(repo).getRecent(5);
        
        assertEquals(5, ret.size());
        assertEquals(images, ret);
    }

    @Test
    public void testSave() throws IOException {
        Image image = new Image(loadSamplePNG());

        service.save(image);

        verify(repo).save(image);
        
        assertNotNull(image.getThumbnail());
    }
}
