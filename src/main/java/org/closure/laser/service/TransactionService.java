package org.closure.laser.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.closure.laser.domain.Transaction;
import org.closure.laser.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Transaction}.
 */
@Service
@Transactional
public class TransactionService {

    private final Logger log = LoggerFactory.getLogger(TransactionService.class);

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     * Save a transaction.
     *
     * @param transaction the entity to save.
     * @return the persisted entity.
     */
    public Transaction save(Transaction transaction) {
        log.debug("Request to save Transaction : {}", transaction);
        return transactionRepository.save(transaction);
    }

    /**
     * Update a transaction.
     *
     * @param transaction the entity to save.
     * @return the persisted entity.
     */
    public Transaction update(Transaction transaction) {
        log.debug("Request to save Transaction : {}", transaction);
        return transactionRepository.save(transaction);
    }

    /**
     * Partially update a transaction.
     *
     * @param transaction the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Transaction> partialUpdate(Transaction transaction) {
        log.debug("Request to partially update Transaction : {}", transaction);

        return transactionRepository
            .findById(transaction.getId())
            .map(existingTransaction -> {
                if (transaction.getFromAccount() != null) {
                    existingTransaction.setFromAccount(transaction.getFromAccount());
                }
                if (transaction.getToAccount() != null) {
                    existingTransaction.setToAccount(transaction.getToAccount());
                }
                if (transaction.getFees() != null) {
                    existingTransaction.setFees(transaction.getFees());
                }
                if (transaction.getNetAmount() != null) {
                    existingTransaction.setNetAmount(transaction.getNetAmount());
                }

                return existingTransaction;
            })
            .map(transactionRepository::save);
    }

    /**
     * Get all the transactions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Transaction> findAll(Pageable pageable) {
        log.debug("Request to get all Transactions");
        return transactionRepository.findAll(pageable);
    }

    /**
     *  Get all the transactions where Deal is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Transaction> findAllWhereDealIsNull() {
        log.debug("Request to get all transactions where Deal is null");
        return StreamSupport
            .stream(transactionRepository.findAll().spliterator(), false)
            .filter(transaction -> transaction.getDeal() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one transaction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Transaction> findOne(Long id) {
        log.debug("Request to get Transaction : {}", id);
        return transactionRepository.findById(id);
    }

    /**
     * Delete the transaction by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Transaction : {}", id);
        transactionRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<Transaction> search(String key, String value, Pageable pageable) {
        log.debug("Request to get all shipment type by key");
        return transactionRepository.search(key, value, pageable);
    }
}
