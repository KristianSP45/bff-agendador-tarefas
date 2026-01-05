package com.kristian.bffagendadortarefas.business;

import com.kristian.bffagendadortarefas.business.dto.in.EnderecoDTORequest;
import com.kristian.bffagendadortarefas.business.dto.in.LoginRequestDTO;
import com.kristian.bffagendadortarefas.business.dto.in.TelefoneDTORequest;
import com.kristian.bffagendadortarefas.business.dto.in.UsuarioDTORequest;
import com.kristian.bffagendadortarefas.business.dto.out.EnderecoDTOResponse;
import com.kristian.bffagendadortarefas.business.dto.out.TelefoneDTOResponse;
import com.kristian.bffagendadortarefas.business.dto.out.UsuarioDTOResponse;
import com.kristian.bffagendadortarefas.infractructure.client.UsuarioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioClient usuarioClient;

    public UsuarioDTOResponse salvarUsuario(UsuarioDTORequest usuarioDTO){
        return usuarioClient.salvarUsuario(usuarioDTO);
    }

    public String loginUsuario(LoginRequestDTO dto){
        return  usuarioClient.login(dto);
    }

    public UsuarioDTOResponse buscarUsuarioPorEmail(String email, String token){
        return usuarioClient.buscarUsuarioPorEmail(email, token);
    }

    public void deletaUsuarioPorEmail(String email, String token){
        usuarioClient.deletarUsuarioPorEmail(email, token);
    }

    public UsuarioDTOResponse atualizaDadosUsuario(String token, UsuarioDTORequest dto){
        return usuarioClient.atualizarDadoUsuario(dto, token);
    }

    public EnderecoDTOResponse atualizaEndereco(Long idEndereco, EnderecoDTORequest dto, String token){
        return usuarioClient.atualizaEndereco(dto, idEndereco, token);
    }

    public TelefoneDTOResponse atualizaTelefone(Long idTelefone, TelefoneDTORequest dto, String token){
        return usuarioClient.atualizaTelefone(dto, idTelefone, token);
    }

    public EnderecoDTOResponse cadastraEndereco(String token, EnderecoDTORequest dto){
        return usuarioClient.cadastraEndereco(dto, token);
    }

    public TelefoneDTOResponse cadastraTelefone(String token, TelefoneDTORequest dto){
        return usuarioClient.cadastraTelefone(dto, token);
    }
}
