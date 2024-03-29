package org.closure.laser.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.closure.laser.domain.Connection;
import org.closure.laser.repository.ConnectionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Connection}.
 */
@Service
@Transactional
public class ConnectionService {

    private final Logger log = LoggerFactory.getLogger(ConnectionService.class);

    private final ConnectionRepository connectionRepository;

    public ConnectionService(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }

    /**
     * Save a connection.
     *
     * @param connection the entity to save.
     * @return the persisted entity.
     */
    public Connection save(Connection connection) {
        log.debug("Request to save Connection : {}", connection);
        return connectionRepository.save(connection);
    }

    /**
     * Update a connection.
     *
     * @param connection the entity to save.
     * @return the persisted entity.
     */
    public Connection update(Connection connection) {
        log.debug("Request to save Connection : {}", connection);
        return connectionRepository.save(connection);
    }

    /**
     * Partially update a connection.
     *
     * @param connection the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Connection> partialUpdate(Connection connection) {
        log.debug("Request to partially update Connection : {}", connection);

        return connectionRepository
            .findById(connection.getId())
            .map(existingConnection -> {
                if (connection.getFcmToken() != null) {
                    existingConnection.setFcmToken(connection.getFcmToken());
                }
                if (connection.getLocalToken() != null) {
                    existingConnection.setLocalToken(connection.getLocalToken());
                }
                if (connection.getLocalRefreshToken() != null) {
                    existingConnection.setLocalRefreshToken(connection.getLocalRefreshToken());
                }
                if (connection.getoAuthToken() != null) {
                    existingConnection.setoAuthToken(connection.getoAuthToken());
                }
                if (connection.getLocalTokenExpiryDate() != null) {
                    existingConnection.setLocalTokenExpiryDate(connection.getLocalTokenExpiryDate());
                }

                return existingConnection;
            })
            .map(connectionRepository::save);
    }

    /**
     * Get all the connections.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Connection> findAll(Pageable pageable) {
        log.debug("Request to get all Connections");
        return connectionRepository.findAll(pageable);
    }

    /**
     *  Get all the connections where UserApplication is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Connection> findAllWhereUserApplicationIsNull() {
        log.debug("Request to get all connections where UserApplication is null");
        return StreamSupport
            .stream(connectionRepository.findAll().spliterator(), false)
            .filter(connection -> connection.getUserApplication() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one connection by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Connection> findOne(Long id) {
        log.debug("Request to get Connection : {}", id);
        return connectionRepository.findById(id);
    }

    /**
     * Delete the connection by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Connection : {}", id);
        connectionRepository.deleteById(id);
    }
}
