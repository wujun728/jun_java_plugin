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
package com.github.jmitchener.repository;

import static com.github.jmitchener.util.ImageTestUtil.getValidImage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.github.jmitchener.model.Image;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
    "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
    "file:src/main/webapp/WEB-INF/spring/root-context.xml"
})
@Transactional
public class JPAImageRepositoryTest {
    
    @Inject
    private ImageRepository repo;
    
    @Test
    public void testFindAll() throws IOException {
        assertEquals(0, repo.findAll().size());
    }
    
    @Test
    public void testSave() throws IOException {
        Image image = getValidImage();
        
        repo.save(image);
        
        assertNotNull(image.getId());
        assertEquals(1, repo.findAll().size());
    }

    @Test
    public void testDelete() throws IOException {
        Image image = getValidImage();
        
        repo.save(image);
        
        assertEquals(1, repo.findAll().size());
        
        repo.delete(image);
        
        assertEquals(0, repo.findAll().size());
    }
    
    @Test
    public void testSaveCollection() throws IOException {
        List<Image> _images = new ArrayList<Image>();
        
        for (int i = 0; i < 2; i++)
            _images.add(getValidImage());
        
        repo.save(_images);
        
        assertEquals(2, repo.findAll().size());
    }
}
