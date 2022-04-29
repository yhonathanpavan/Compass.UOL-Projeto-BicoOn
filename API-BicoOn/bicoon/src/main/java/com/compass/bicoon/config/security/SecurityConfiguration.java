package com.compass.bicoon.config.security;

import com.compass.bicoon.repository.ClienteRepository;
import com.compass.bicoon.repository.PrestadorRepository;
import com.compass.bicoon.services.autenticacao.AutenticacaoService;
import com.compass.bicoon.services.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@Profile("prod")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PrestadorRepository prestadorRepository;

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    //Configurações de autenticaçação
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
    }

    //Configurações de Autorização
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers(HttpMethod.POST, "/bicoon/prestadores").permitAll() //Permite somente cadastro
                .antMatchers(HttpMethod.POST, "/bicoon/clientes").permitAll() //Permite somente cadastro
                .antMatchers(HttpMethod.POST, "/bicoon/auth").permitAll() //Permite o login
                .antMatchers("/bicoon/categorias/**").hasRole("ADMINISTRADOR")
                .anyRequest().authenticated()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().headers().frameOptions().sameOrigin()
                .and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, clienteRepository, prestadorRepository), UsernamePasswordAuthenticationFilter.class);
    }


}
