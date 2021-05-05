package com.butlersuite.djinn.utils.convert;

public interface Transformer<E, D> {

   D toDTO (E e);

   E toEntity(D d);
}
