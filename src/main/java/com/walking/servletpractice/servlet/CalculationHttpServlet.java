package com.walking.servletpractice.servlet;

import com.walking.servletpractice.converter.CalculationConverter;
import com.walking.servletpractice.converter.CalculationRequestConverter;
import com.walking.servletpractice.model.Calculation;
import com.walking.servletpractice.model.dto.CalculationDto;
import com.walking.servletpractice.service.CalculationService;
import com.walking.servletpractice.service.CalculationStorageService;
import com.walking.servletpractice.service.CalculationRequestParsingService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * Позволяет получать результаты заданных вычислений через различные HTTP-запросы.
 * Хранит и обеспечивает доступ к истории произведенных вычислений в рамках отдельного запуска приложения.
 */
@WebServlet("/")
public class CalculationHttpServlet extends HttpServlet {
    private CalculationRequestParsingService calculationRequestParsingService;
    private CalculationRequestConverter calculationRequestConverter;
    private CalculationStorageService calculationStorageService;
    private CalculationConverter calculationConverter;

    @Override
    public void init() throws ServletException {
        calculationRequestParsingService = new CalculationRequestParsingService();

        var calculationService = new CalculationService();
        calculationRequestConverter = new CalculationRequestConverter(calculationService);

        calculationStorageService = new CalculationStorageService();

        calculationConverter = new CalculationConverter();
    }

    /**
     * Извлекает строку из тела HTTP-запроса, парсит ее в dto-объект {@code CalculationRequest}.
     * Преобразует dto-объект {@code CalculationRequest} в объект бизнес-логики {@code Calculation},
     * в процессе преобразования происходит вычисление, результат которого содержится в объекте
     * {@code Calculation}. Сохраняет объект {@code Calculation} для дальнейшего использования.
     * Преобразует объект {@code Calculation} в dto-объект {@code CalculationDto}. Отправляет
     * dto-объект {@code CalculationDto} в HTTP-ответ в виде строки.<p>
     * В случае возникновения RuntimeException - посылает в HTTP-ответ ошибку с кодом 400.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {

        String requestData = req.getReader()
                                .lines()
                                .collect(Collectors.joining());

        try {
            var calculationRequest = calculationRequestParsingService.parse(requestData);

            Calculation calculation = calculationRequestConverter.convert(calculationRequest);

            calculationStorageService.save(calculation);

            CalculationDto calculationDto = calculationConverter.convert(calculation);

            resp.getWriter()
                .print(calculationDto.toString());
        } catch (RuntimeException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    /**
     * В случае получения HTTP-запроса с заданным значением параметра 'history', возвращает
     * строку содержащую информацию о произведенных вычислениях.<p>
     * В случае отсутствия параметра 'history' или его неверном значении - посылает в HTTP-ответ
     * ошибку с кодом 400.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {

        String historyValue = req.getParameter("history");

        if (historyValue != null) {
            switch (historyValue) {
                case "all" -> sendAllHistory(resp);

                default -> resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void sendAllHistory(HttpServletResponse resp) throws IOException {
        var calculationDtoJoiner = new StringJoiner("\n");

        for (Calculation calculation : calculationStorageService.getAll()) {
            CalculationDto calculationDto = calculationConverter.convert(calculation);

            calculationDtoJoiner.add(calculationDto.toString());
        }

        resp.getWriter()
            .print(calculationDtoJoiner);
    }
}
