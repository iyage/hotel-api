package com.api.service.serviceImpl;

import com.api.model.Photos;
import com.api.repository.PhotoRepository;
import com.api.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotoServiceImpl implements PhotoService {
    @Autowired
    PhotoRepository photoRepository;
    @Override
    public Photos newPhoto(Photos photo) {
        return  photoRepository.save(photo);
    }
}
