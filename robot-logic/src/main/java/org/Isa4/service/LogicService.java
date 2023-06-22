package org.Isa4.service;

import lombok.RequiredArgsConstructor;
import org.Isa4.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogicService {
    private final TransactionRepository transactionRepository;

    public void run (){
        try {
            while (true){
          //      System.out.println("Привет");
                Thread.sleep(2000);
            }
        } catch (Exception e){}
    }

}
