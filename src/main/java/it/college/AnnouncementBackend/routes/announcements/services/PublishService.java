package it.college.AnnouncementBackend.routes.announcements.services;

import it.college.AnnouncementBackend.core.domain.model.entity.Announcement;
import it.college.AnnouncementBackend.core.domain.model.enums.AStatus;
import it.college.AnnouncementBackend.core.domain.repository.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.util.List;

/**
 * ## Сервис выставления объявление по заданой дате
 *
 * @author Горелов Дмитрий
 * */

@Service
@RequiredArgsConstructor
public class PublishService {

    private final AnnouncementRepository repository;

    /**
     * Выложить объявление, если подходящая дата
     */

    @Scheduled(cron = "0 0 4 * * ?") // Каждый день в 4 часа ночи
    public void TaskToPublish(){
        try {
            List<Announcement> announcements = this.repository.findAllByStatus(AStatus.WaitPublish);

            for (Announcement announcement : announcements){
                if (announcement.getPublishDate().equals(LocalDate.now())){
                    announcement.setStatus(AStatus.Public);
                    this.repository.save(announcement);

                    System.out.println("Объявление опубликовано: " + announcement.getUUID());
                }
            }

            System.out.println("Объявления в дату - " + LocalDate.now() + ", Выложены успешно");
        }catch (Exception err){
            System.err.println("Ошибка при выставление объявление - " + err.getMessage());
        }
    }

    /**
     * Перенести в архив объявление, если подходящая дата
     */

    @Scheduled(cron = "0 0 2 * * ?") // Каждый день в 4 часа ночи
    public void TaskToArchive(){
        try {
            List<Announcement> announcements = this.repository.findAllByStatus(AStatus.WaitPublish);

            for (Announcement announcement : announcements){
                if (announcement.getDeleteDate().equals(LocalDate.now())){
                    announcement.setStatus(AStatus.Archive);
                    this.repository.save(announcement);

                    System.out.println("Объявление перенесено в архив: " + announcement.getUUID());
                }
            }

            System.out.println("Объявления в дату - " + LocalDate.now() + ", успешно удалены");
        }catch (Exception err){
            System.err.println("Ошибка при переносе объявлений в архив - " + err.getMessage());
        }
    }
}
