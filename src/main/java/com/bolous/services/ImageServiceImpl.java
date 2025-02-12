package com.bolous.services;

import com.bolous.domain.Recipe;
import com.bolous.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeService) {
        this.recipeRepository = recipeService;
    }

    @Override
    public void saveImageFile(Long id, MultipartFile file){

       try {
           Recipe recipe = recipeRepository.findById(id).get();

           Byte[] byteObjects = new Byte[file.getBytes().length];

           int i = 0;

           for(byte b : file.getBytes()) {
               byteObjects[i++] = b;
           }

           recipe.setImage(byteObjects);
           recipeRepository.save(recipe);

       } catch (IOException e) {

           log.error("Error occurred while saving image file", e);
           e.printStackTrace();
       }

    }
}
