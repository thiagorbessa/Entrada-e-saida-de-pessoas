package com.br.entrada.saida.pessoas.configuration;

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
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Arquivos estáticos e login público
                        .requestMatchers("/", "/css/**", "/js/**", "/images/**").permitAll()

                        // Gestão de usuários restrita a administradores
                        .requestMatchers("/usuarios/**").hasAnyRole("SISTEMA", "GERAL")

                        // Exclusão de pessoas: restrita estritamente a administradores
                        .requestMatchers(HttpMethod.DELETE, "/pessoas/**").hasAnyRole("SISTEMA", "GERAL")

                        // Listagem, cadastro, edição e histórico de pessoas: qualquer perfil autenticado acessa
                        .requestMatchers("/pessoas", "/pessoas/**").authenticated()

                        // Qualquer outra rota exige autenticação
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/pessoas", true)
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