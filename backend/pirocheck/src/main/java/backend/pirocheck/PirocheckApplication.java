package backend.pirocheck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class PirocheckApplication {

    public static void main(String[] args) {

        // .env 파일 로드
        Dotenv dotenv = Dotenv.configure()
                .directory("./") // .env 파일 경로 설정 (기본: 프로젝트 루트)
                .load();

        // 환경변수를 시스템 프로퍼티에 추가
        dotenv.entries().forEach(entry ->
                System.setProperty(entry.getKey(), entry.getValue())
        );

        SpringApplication.run(PirocheckApplication.class, args);
    }

}
