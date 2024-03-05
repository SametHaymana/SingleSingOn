package com.sso.api.modules.auth.services;

import com.sso.api.models.Client;
import com.sso.api.repositories.ClientRepository;
import com.sso.api.utils.responses.ApiErrors.NotFoundError;
import com.sso.api.utils.responses.ApiResponseCodes;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
  private final ClientRepository clientRepository;

  public ClientService(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  public Client getClient(UUID clientId) {
    Client client = clientRepository.findByUid(clientId).orElse(null);
    if (client == null) {
      throw new NotFoundError(ApiResponseCodes.ClientNotFound);
    }
    return client;
  }
}
