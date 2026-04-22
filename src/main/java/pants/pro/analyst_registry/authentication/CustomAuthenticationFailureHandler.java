package pants.pro.analyst_registry.authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
/**
 * Authentication failure handler that logs attempts and redirects with error context.
 */
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     * Logs a failed login and redirects with an error flag.
     * @param request HTTP request with login details.
     * @param response HTTP response used for redirect.
     * @param exception authentication failure details.
     * @throws IOException if redirect fails.
     * @throws ServletException if servlet handling fails.
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {

            String username = request.getParameter("username");
            String ipAddress = getClientIp(request);
            String userAgent = request.getHeader("User-Agent");


            log.warn("Authentication failed for username={} from ip{} and userAgent={}",
                    username,
                    ipAddress,
                    userAgent);

            if (exception instanceof DisabledException) {
                redirectStrategy.sendRedirect(request, response, "/login?disabled");
            } else if (exception instanceof LockedException) {
                redirectStrategy.sendRedirect(request, response, "/login?locked");
            } else {
                redirectStrategy.sendRedirect(request, response, "/login?error");
            }

    }

    private String getClientIp(HttpServletRequest request) {

        String xfHeader = request.getHeader("X-FORWARDED-FRO");

        if (xfHeader == null) {
            return request.getRemoteAddr();
        }

        return xfHeader.split(",")[0];
    }
}
