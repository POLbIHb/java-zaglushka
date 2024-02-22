package com.example.newMock2.Controller;

import com.example.newMock2.Model.RequestDTO;
import com.example.newMock2.Model.ResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;
import java.util.Random;

@RestController
public class MainController {

    private Logger log = LoggerFactory.getLogger(MainController.class);
    ObjectMapper mapper = new ObjectMapper();

    @PostMapping(
            value = "/info/postBalances",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Object postBalances(@RequestBody RequestDTO requestDTO){
        try{
            String clientId = requestDTO.getClientId();
            char firstDigit = clientId.charAt(0);
            BigDecimal maxLimit;
            String valuta;
            String RqUID = requestDTO.getRqUID();

            if (firstDigit == '8') {
                valuta = new String("US");
                maxLimit = new BigDecimal(2000);
            } else if (firstDigit == '9') {
                valuta = new String("EU");
                maxLimit = new BigDecimal(1000);
            } else {
                valuta = new String("RUB");
                maxLimit = new BigDecimal(10000);
            }

            ResponseDTO responseDTO = new ResponseDTO();

            responseDTO.setRqUID(RqUID);
            responseDTO.setClientId(clientId);
            responseDTO.setAccount(requestDTO.getAccount());
            responseDTO.setCurrency(valuta);
            responseDTO.setBalance(String.valueOf(new BigDecimal(new Random().nextInt(maxLimit.intValue()))));
            responseDTO.setMaxLimit(String.valueOf(maxLimit));

            log.error("******** Запрос ********" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.error("******** Ответ ********" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));

            return responseDTO;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
