package com.api.service;

import com.api.model.Photos;
import org.springframework.stereotype.Service;

@Service
public interface PhotoService {
  Photos newPhoto(Photos photo);
}
