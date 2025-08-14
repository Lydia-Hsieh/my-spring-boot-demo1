package com.example.my_spring_boot_demo1.controller.testPropagationController;

import com.example.my_spring_boot_demo1.dao.repository.TestPropagationRepository;
import com.example.my_spring_boot_demo1.entity.TestPropagationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BService {

    @Autowired
    private TestPropagationRepository repository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void BHasTransRequired() {
        repository.save(new TestPropagationEntity("B"));
        int i = 1/0;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void BHasTransRequiresNew() {
        repository.save(new TestPropagationEntity("B"));
        int i = 1/0;
    }
}
