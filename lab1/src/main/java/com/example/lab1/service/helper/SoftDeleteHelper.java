package com.example.lab1.service.helper;

import com.example.lab1.entity.interfaces.SoftDeletable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Component
public class SoftDeleteHelper {

    public <T extends SoftDeletable> void softDelete(
            Long id,
            Function<Long, Optional<T>> findById,
            Consumer<T> saveEntity,
            Supplier<? extends RuntimeException> notFoundSupplier
    ) {
        T entity = findById.apply(id).orElseThrow(notFoundSupplier);

        if (entity.isDeleted()) {
            ResponseEntity.ok().build();
            return;
        }

        entity.setDeleted(true);
        entity.setDeletedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        saveEntity.accept(entity);

        ResponseEntity.ok().build();
    }
}
