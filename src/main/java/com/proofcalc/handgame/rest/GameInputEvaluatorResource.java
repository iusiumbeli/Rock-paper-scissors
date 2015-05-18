package com.proofcalc.handgame.rest;

import com.proofcalc.handgame.model.GameConfig;
import com.proofcalc.handgame.model.strategy.Outcome;
import com.proofcalc.handgame.model.ZeroSumHandGame;
import com.proofcalc.handgame.model.ZeroSumHandGameFactory;
import com.proofcalc.handgame.model.GameOutcome;
import com.proofcalc.handgame.util.JsonConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Component
@Scope("request")
@Path("game")
public class GameInputEvaluatorResource {

    private static final Logger logger = LoggerFactory.getLogger(GameInputEvaluatorResource.class);

    @Path("input")
    @Produces("application/json")
    @POST
    public Response evaluateGameInput(@Context HttpServletRequest request,
                                      @FormParam("gameType") String gameType,
                                      @FormParam("playerConfig") String playerConfig,
                                      @FormParam("userChoice") String userChoice) {
        logger.info("gameType: {}", gameType);
        logger.info("playerConfig: {}", playerConfig);
        logger.info("userChoice: {}", userChoice);

        GameConfig gameConfig = GameConfig.from(gameType, playerConfig, userChoice);
        ZeroSumHandGame game = ZeroSumHandGameFactory.getGameBy(gameConfig);
        GameOutcome gameOutcome = game.play();

        String output = JsonConverter.toJson(gameOutcome);
        return Response.ok(output).build();
    }

}
