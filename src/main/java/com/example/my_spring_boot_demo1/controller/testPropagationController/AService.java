package com.example.my_spring_boot_demo1.controller.testPropagationController;

import com.example.my_spring_boot_demo1.dao.repository.TestPropagationRepository;
import com.example.my_spring_boot_demo1.entity.TestPropagationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AService {

    @Autowired
    private TestPropagationRepository repository;

    @Autowired
    private BService bService;

    // 1. A未開啟交易，B開啟交易且propagation=required
    public void ANoTransWhenBHasTransRequired() {
        repository.save(new TestPropagationEntity("A"));
        bService.BHasTransRequired();
        // 預測: B開啟新交易+rollback，A不受影響
        // 實際: 預測正確
    }

    // 2. A未開啟交易，B開啟交易且propagation=requires new
    public void ANoTransWhenBHasTransRequiresNew() {
        repository.save(new TestPropagationEntity("A"));
        bService.BHasTransRequiresNew();
        // 預測: B開啟新交易+rollback，A不受影響
        // 實際: 預測正確
    }

    // 3. A開啟交易，B開啟交易且propagation=required
    @Transactional
    public void AHasTransWhenBHasTransRequired() {
        repository.save(new TestPropagationEntity("A"));
        bService.BHasTransRequired();
        // 預測: B沿用A的交易，rollback時A和B一起rollback
        // 實際: 預測正確
    }

    // 4. A開啟交易，B開啟交易且propagation=requires new
    @Transactional
    public void AHasTransWhenBHasTransRequiresNew() {
        repository.save(new TestPropagationEntity("A"));
        bService.BHasTransRequiresNew();
        // 預測: B不沿用A的交易，B自己開一個新交易；
        //      B會rollback，A雖然屬於不同事務但也會被標上rollback-only導致rollback
        // 實際: 預測正確
    }

    // 5. A開啟交易並以try-catch處理B冒泡上來的異常，B開啟交易且propagation=requires new
    @Transactional
    public void AHasTransWithTryCatchWhenBHasTransRequiresNew() {
        repository.save(new TestPropagationEntity("A"));
        try {
            bService.BHasTransRequiresNew();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 預測: B不沿用A的交易，B自己開一個新交易；
        //      B會rollback，A雖然接收到B的RuntimeException但被try-catch捕捉，所以A不會rollback
        // 實際: 預測正確
    }
}
