package com.example.lab2.sorting_bin.entity.interfaces;

import java.time.LocalDateTime;

/**
 * Common contract for entities that support soft deletion.
 */
public interface SoftDeletable {
    boolean isDeleted();
    void setDeleted(boolean deleted);
    void setDeletedAt(LocalDateTime deletedAt);
    void setUpdatedAt(LocalDateTime updatedAt);
}
