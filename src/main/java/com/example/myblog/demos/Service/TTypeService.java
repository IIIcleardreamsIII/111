package com.example.myblog.demos.Service;


import com.example.myblog.demos.Repository.TTypeRepository;
import com.example.myblog.demos.pojo.TType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TTypeService {

    private final TTypeRepository typeRepository;

    @Autowired
    public TTypeService(TTypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    public List<TType> findAllTypes() {
        return typeRepository.findAll();
    }

    public TType findTypeById(Long id) {
        Optional<TType> type = typeRepository.findById(id);
        return type.orElse(null);
    }

    public TType saveType(TType type) {
        return typeRepository.save(type);
    }

    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }
}
