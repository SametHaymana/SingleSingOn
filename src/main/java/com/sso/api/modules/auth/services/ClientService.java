package com.sso.api.modules.auth.services;

import com.sso.api.models.Client;
import com.sso.api.repositories.ClientRepository;
import com.sso.api.utils.responses.ApiErrors.NotFoundError;
import com.sso.api.utils.responses.ApiResponseCodes;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
  private final ClientRepository clientRepository;

  public ClientService(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  public Client getClient(UUID clientId) {
    return clientRepository
      .findByUid(clientId)
      .orElseThrow(() -> new NotFoundError(ApiResponseCodes.ClientNotFound));
  }
}
