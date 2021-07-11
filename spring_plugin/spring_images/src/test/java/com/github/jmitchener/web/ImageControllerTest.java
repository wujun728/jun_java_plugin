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
package com.github.jmitchener.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

import com.github.jmitchener.model.Image;
import com.github.jmitchener.service.ImageService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
    "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
    "file:src/main/webapp/WEB-INF/spring/root-context.xml"
})
public class ImageControllerTest {
    
    @Inject
    private ApplicationContext appCtx;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private HandlerAdapter handlerAdapter;

    private ImageController controller;
    private ImageService service;

    @Before
    public void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        handlerAdapter = appCtx.getBean(AnnotationMethodHandlerAdapter.class);
        service = mock(ImageService.class);
        controller = new ImageController(service);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testDisplaysTenMostRecentImages() throws Exception {
        request.setMethod("GET");
        request.setRequestURI("/images");
        
        List<Image> _recentImages = new ArrayList<Image>();
        
        for (int i = 0; i < 10; i++)
            _recentImages.add(new Image());
        
        when(service.getRecent(10)).thenReturn(_recentImages);
        
        ModelAndView mav = handlerAdapter.handle(request, response, controller);

        verify(service).getRecent(10);
        
        Map<String, Object> model = mav.getModel();
        List<Image> recentImages = (List<Image>) model.get("recentImages");
        
        assertEquals(10, recentImages.size());
        assertEquals("/images/index", mav.getViewName());
    }
    
    @Test
    public void testRetrieveImageById() throws Exception {
        request.setMethod("GET");
        request.setRequestURI("/images/27");
        
        when(service.find(27L)).thenReturn(new Image());
        
        ModelAndView mav = handlerAdapter.handle(request, response, controller);
        Map<String, Object> model = mav.getModel();
        
        verify(service).find(27L);
        
        assertNotNull(model.get("image"));
        assertEquals("/images/show", mav.getViewName());
    }
}