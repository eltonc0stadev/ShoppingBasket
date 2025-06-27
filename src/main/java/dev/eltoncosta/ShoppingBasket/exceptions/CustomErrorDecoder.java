package dev.eltoncosta.ShoppingBasket.exceptions;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400:
                return new DataNotFoundException("Product not found: " + response.reason());
            default:
                return new Exception("Generic error: " + response.reason());
        }
    }
}
