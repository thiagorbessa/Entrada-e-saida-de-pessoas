package com.br.entrada.saida.pessoas.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TwoFactorAuthenticationSuccessHandler twoFactorSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Arquivos estáticos e login público
                        .requestMatchers("/", "/css/**", "/js/**", "/images/**").permitAll()

                        // Tela de validação do código de 6 dígitos (após validar CPF e senha)
                        .requestMatchers("/login-2fa").hasAuthority(TwoFactorAuthenticationSuccessHandler.ROLE_PRE_AUTH_2FA)

                        // Configuração de 2FA do próprio usuário logado
                        .requestMatchers("/2fa/**").hasAnyRole("USER", "SISTEMA", "GERAL")

                        // Gestão de usuários restrita a administradores
                        .requestMatchers("/usuarios/alterar-senha-propria").hasAnyRole("USER", "SISTEMA", "GERAL")
                        .requestMatchers("/usuarios/**").hasAnyRole("SISTEMA", "GERAL")

                        // Exclusão de pessoas: restrita estritamente a administradores
                        .requestMatchers(HttpMethod.DELETE, "/pessoas/**").hasAnyRole("SISTEMA", "GERAL")

                        // Listagem, cadastro, edição e histórico de pessoas
                        .requestMatchers("/pessoas", "/pessoas/**").hasAnyRole("USER", "SISTEMA", "GERAL")

                        // Qualquer outra rota exige login completo
                        .anyRequest().hasAnyRole("USER", "SISTEMA", "GERAL")
                )
                .formLogin(form -> form
                        .loginPage("/")
                        .loginProcessingUrl("/login")
                        .successHandler(twoFactorSuccessHandler)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/")
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}