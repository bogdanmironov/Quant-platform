package bg.mironov.bogdan.quant.backend.controller;

import bg.mironov.bogdan.quant.backend.model.TradingSignal;
import bg.mironov.bogdan.quant.backend.service.SignalQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/signals")
public class SignalController {
    private final SignalQueryService service;

    public SignalController(SignalQueryService service) {
        this.service = service;
    }

    @GetMapping("/latest")
    public List<TradingSignal> latest(
        @RequestParam(defaultValue = "20") int limit
    ) {
        return service.getLatestSignals(limit);
    }

    @GetMapping("/symbol/{symbol}")
    public List<TradingSignal> signalsBySymbol(@PathVariable String symbol) {
        return service.getSignalsBySymbol(symbol);
    }

    @GetMapping("/type/{type}")
    public List<TradingSignal> signalsByType(@PathVariable String type) {
        return service.getSignalsByType(type.toUpperCase());
    }
}
