package com.butlersuite.djinn.utils.convert;

public interface Transformer <E, D>{

   E toEntity(D d);

   D toDTO(E e);
}
