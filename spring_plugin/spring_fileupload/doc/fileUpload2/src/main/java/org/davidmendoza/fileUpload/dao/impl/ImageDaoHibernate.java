/*
 * The MIT License
 *
 * Copyright 2013 jdmr.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.davidmendoza.fileUpload.dao.impl;

import java.util.List;
import org.davidmendoza.fileUpload.dao.ImageDao;
import org.davidmendoza.fileUpload.model.Image;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jdmr
 */
@Repository
@Transactional
public class ImageDaoHibernate implements ImageDao {
    
    private static final Logger log = LoggerFactory.getLogger(ImageDaoHibernate.class);
    
    @Autowired
    private SessionFactory sessionFactory;
    
    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Image> list() {
        log.debug("List of images");
        Query query = currentSession().getNamedQuery("images");
        List<Image> images = query.list();
        return images;
    }

    @Override
    public Image create(Image image) {
        log.debug("Creating image");
        currentSession().save(image);
        return image;
    }

    @Override
    @Transactional(readOnly = true)
    public Image get(Long id) {
        log.debug("Getting image {}", id);
        Image image = (Image) currentSession().get(Image.class, id);
        return image;
    }

    @Override
    public void delete(Image image) {
        log.debug("Deleting image {}", image.getName());
        currentSession().delete(image);
    }
    
}
