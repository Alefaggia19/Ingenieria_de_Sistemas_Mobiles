package com.checkit.checkit_backend.service;

import com.checkit.checkit_backend.dto.KpiResponseDto;
import com.checkit.checkit_backend.repository.*;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class KpiService {
    private final UserRepository userRepository;
    private final ChallengeRepository challengeRepository;
    private final NpsRepository npsRepository;
    private final UserSessionRepository sessionRepository;
    private final TaskCompletionRepository taskCompletionRepository;

    public KpiService(UserRepository userRepository, ChallengeRepository challengeRepository, 
                      NpsRepository npsRepository, UserSessionRepository sessionRepository,
                    TaskCompletionRepository taskCompletionRepository) {
        this.userRepository = userRepository;
        this.challengeRepository = challengeRepository;
        this.npsRepository = npsRepository;
        this.sessionRepository = sessionRepository;
        this.taskCompletionRepository = taskCompletionRepository;
    }

    public KpiResponseDto calculateKpis() {
        KpiResponseDto dto = new KpiResponseDto();
        
        // 1. Crecimiento de usuarios [cite: 48]
        dto.setTotalUsers(userRepository.count());

        // 2. Desafíos creados esta semana [cite: 50]
        LocalDateTime lastWeek = LocalDateTime.now().minusWeeks(1);
        dto.setChallengesCreatedThisWeek(challengeRepository.countByCreationDateAfter(lastWeek));

        // 3. Media NPS 
        dto.setAverageNps(npsRepository.findAverageScore().orElse(0.0));

        // 4. Tasa de conversión 
        long totalSessions = sessionRepository.count();
        long totalConversions = challengeRepository.count(); // Esempio: sfide create
        
        if (totalSessions > 0) {
            double rate = ((double) totalConversions / totalSessions) * 100;
            dto.setConversionRate(rate);
        }

        // CALCULATE TASK COMPLETED TODAY
        // Imposta l'inizio della giornata (00:00:00) per filtrare correttamente
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        dto.setTasksCompletedToday(taskCompletionRepository.countByCompletedAtAfter(startOfDay));

        return dto;
    }
}
