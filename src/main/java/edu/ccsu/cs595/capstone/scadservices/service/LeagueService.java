package edu.ccsu.cs595.capstone.scadservices.service;

import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.ccsu.cs595.capstone.scadservices.api.UserApi;
import edu.ccsu.cs595.capstone.scadservices.dao.SCADLeagueDao;
import edu.ccsu.cs595.capstone.scadservices.dto.SCADLeagueDto;
import edu.ccsu.cs595.capstone.scadservices.dto.UserDto;
import edu.ccsu.cs595.capstone.scadservices.entity.SCADLeague;
import edu.ccsu.cs595.capstone.scadservices.security.SCADSecurityManager;

@Stateless
public class LeagueService {

	private static final Logger LOG = LoggerFactory.getLogger(LeagueService.class);

	@Inject
	UserApi userApi;

	@Inject
	SCADSecurityManager sm;
	
	@Inject
	SCADLeagueDao slDao;

	private static final String YAHOORESTURI_GAMEINFO = "https://fantasysports.yahooapis.com/fantasy/v2/game/nfl?format=json";
	private static final String YAHOORESTURI_USERLEAGUE = "https://fantasysports.yahooapis.com/fantasy/v2/users;use_login=1/games;game_keys=";
	private static final String YAHOORESTURI_USERLEAGUE_EXT = "/leagues?format=json";

	public String getUserLeague(Long id) {

		String result = null;
		UserDto userDto = new UserDto();
		userDto.setId(1L);
		return result;

	}

	public String getUserAllLeagues() {

		Long s, e;
		s = System.currentTimeMillis();
		String yahooGameStrg = null;
		JsonObject yahooGameObj = null;
		String yahooLeagueStrg = null;
		JsonObject yahooLeagueObj = null;
		String result = null;
		Response response = userApi.getUserInfo();
		UserDto userDto = response.readEntity(UserDto.class);
		String user = userDto.getSub();
		String leagues = "leagues";
		String game = "game";
		yahooGameStrg = getYahooData(YAHOORESTURI_GAMEINFO, user, game);
		yahooGameObj = new JsonParser().parse(yahooGameStrg).getAsJsonObject();
		int gameId = getGameId(yahooGameObj);
		String leagueUrl = YAHOORESTURI_USERLEAGUE + gameId + YAHOORESTURI_USERLEAGUE_EXT;
		yahooLeagueStrg = getYahooData(leagueUrl, user, leagues);
		try {
			if (Objects.nonNull(yahooLeagueStrg)) {
				yahooLeagueObj = new JsonParser().parse(yahooLeagueStrg).getAsJsonObject();
				result = getLeaguesData(yahooLeagueObj);
			}
		} catch (Exception ex) {
			LOG.error("Leagues Json parsing error for user={} - {}", user, ex.getMessage());
		}
		e = System.currentTimeMillis();
		LOG.info("Getting all leagues for user={}, process took {}ms.", user, (e - s));
		return result;

	}
	
	private int getGameId(JsonObject gameObj) {
		
		int gameId = 0;
		JsonElement element = gameObj.get("fantasy_content").getAsJsonObject().get("game").getAsJsonArray().get(0).getAsJsonObject().get("game_id");

		return gameId;
	}

	private String getLeaguesData(JsonObject leaguesObj) {

		String result = null;

		if (Objects.nonNull(leaguesObj)) {
			try {
				JsonElement error = leaguesObj.get("error");
				if (Objects.isNull(error)) {
					JsonObject fantasyContent = leaguesObj.get("fantasy_content").getAsJsonObject();
					JsonObject users = fantasyContent.get("users").getAsJsonObject();
					JsonArray userArray = (users.get("0").getAsJsonObject()).get("user").getAsJsonArray();
					JsonObject games = userArray.get(1).getAsJsonObject().get("games").getAsJsonObject();
					JsonArray gameArray = games.get("0").getAsJsonObject().get("game").getAsJsonArray();
					JsonObject leagues = gameArray.get(1).getAsJsonObject().get("leagues").getAsJsonObject();
					int leagueCnt = leagues.get("count").getAsInt();
					JsonArray newleagues = new JsonArray();
					for (int i = 0; i < leagueCnt; i++) {
						JsonArray league = leagues.get(Integer.toString(i)).getAsJsonObject().get("league").getAsJsonArray();
						newleagues.add(league.get(0));
					}
					result = "{\"leagues\":" + newleagues.toString() + "}";
				} else {
					LOG.error("Leagues object has error: {} ", error);
					result = "ERROR:" + error.toString();
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}

		}

		return result;

	}

	@SuppressWarnings("static-access")
	private String getYahooData(String url, String user, String type) {

		Long s, e;
		s = System.currentTimeMillis();
		boolean success = true;
		String result = null;
		if (Objects.nonNull(url)) {
			Client client = ClientBuilder.newClient();
			WebTarget webTarget = client.target(url);
			String authorization = "Bearer " + sm.getACCESSTOKEN();
			Invocation.Builder builder = webTarget.request(MediaType.APPLICATION_JSON_TYPE)
					.header("Authorization", authorization).header("Accept", "application/json")
					.header("Content-Type", "application/json");
			try {
				Response response = builder.get();
				result = response.readEntity(String.class);
				client.close();
			} catch (Exception ex) {
				success = false;
				LOG.error("Yahoo getting {} request failed for user={}, url={} - {}", type, user, url, ex.getMessage());
			}
		}
		e = System.currentTimeMillis();
		if (success) {
			LOG.info("Yahoo getting {} request was successfull for user={}, and time took {}ms.", type, user, (e - s));
		}
		return result;

	}
	
	public SCADLeagueDto findSCADLeague(Long id) {
		
		SCADLeagueDto result = null;
		SCADLeague slEntity = slDao.find(id);
		result = this.entityToDto(slEntity);
		return result;
		
	}
	
	
	public SCADLeagueDto createSCADLeague(SCADLeagueDto slDto) {
		
		SCADLeagueDto result = null;
		SCADLeague newEntity = this.dtoToEntity(slDto);
		newEntity = slDao.upsert(newEntity);
		result = this.entityToDto(newEntity);
		return result;
		
	}
	
	public SCADLeagueDto updateSCADLeague(Long id, SCADLeagueDto slDto) {
		
		SCADLeagueDto result = null;
		SCADLeague existingEntity = slDao.find(id);
		this.dtoToEntityForUpdate(slDto, existingEntity);
		existingEntity = slDao.upsert(existingEntity);
		result = this.entityToDto(existingEntity);
		return result;
		
	}
	
	public void deleteSCADLeague(Long id) {
		
		SCADLeague deleteEntity = slDao.find(id);
		slDao.delete(deleteEntity);
		
	}
	
	private SCADLeagueDto entityToDto(SCADLeague slEntity) {
		
		SCADLeagueDto result = new SCADLeagueDto();
		
		if (Objects.nonNull(slEntity)) {
			
			result.setId(slEntity.getId());
			result.setYahooLeagueID(slEntity.getYahooLeagueID());
			result.setLeagueManagers(slEntity.getLeagueManagers());
			result.setRookieDraftRds(slEntity.getRookieDraftRds());
			result.setRookieDraftStrategy(slEntity.getRookieDraftStrategy());
			result.setRookieWageScale(slEntity.getRookieWageScale());
			result.setTeamSalaryCap(slEntity.getTeamSalaryCap());
			result.setLeagueSalaryCap(slEntity.getLeagueSalaryCap());
			result.setSalaryCapExemptionLimit(slEntity.getSalaryCapExemptionLimit());
			result.setIrReliefPerc(slEntity.getIrReliefPerc());
			result.setFranchiseTagReliefPerc(slEntity.getFranchiseTagReliefPerc());
			result.setFranchiseTagSpots(slEntity.getFranchiseTagSpots());
			result.setTradingDraftPickYears(slEntity.getTradingDraftPickYears());
			result.setQbMin(slEntity.getQbMin());
			result.setQbMax(slEntity.getQbMax());
			result.setRbMin(slEntity.getRbMin());
			result.setRbMax(slEntity.getRbMax());
			result.setWrMin(slEntity.getWrMin());
			result.setWrMax(slEntity.getWrMax());
			result.setTeMin(slEntity.getTeMin());
			result.setTeMax(slEntity.getTeMax());
			result.setkMin(slEntity.getkMin());
			result.setkMax(slEntity.getkMax());
			result.setDefMin(slEntity.getDefMin());
			result.setDefMax(slEntity.getDefMax());
			result.setIsDefault(slEntity.getIsDefault());
			result.setOwnerId(slEntity.getOwnerId());
			result.setCreatedBy(slEntity.getCreatedBy());
			result.setCreatedAt(slEntity.getCreatedAt());
			result.setModifiedBy(slEntity.getModifiedBy());
			result.setModifiedAt(slEntity.getModifiedAt());
			
		}
		
		return result;
		
	}
	
	private SCADLeague dtoToEntity(SCADLeagueDto slDto) {
		
		SCADLeague result = new SCADLeague();
		
		if (Objects.nonNull(slDto)) {
			
			result.setYahooLeagueID(slDto.getYahooLeagueID());
			result.setLeagueManagers(slDto.getLeagueManagers());
			result.setRookieDraftRds(slDto.getRookieDraftRds());
			result.setRookieDraftStrategy(slDto.getRookieDraftStrategy());
			result.setRookieWageScale(slDto.getRookieWageScale());
			result.setTeamSalaryCap(slDto.getTeamSalaryCap());
			result.setLeagueSalaryCap(slDto.getLeagueSalaryCap());
			result.setSalaryCapExemptionLimit(slDto.getSalaryCapExemptionLimit());
			result.setIrReliefPerc(slDto.getIrReliefPerc());
			result.setFranchiseTagReliefPerc(slDto.getFranchiseTagReliefPerc());
			result.setFranchiseTagSpots(slDto.getFranchiseTagSpots());
			result.setTradingDraftPickYears(slDto.getTradingDraftPickYears());
			result.setQbMin(slDto.getQbMin());
			result.setQbMax(slDto.getQbMax());
			result.setRbMin(slDto.getRbMin());
			result.setRbMax(slDto.getRbMax());
			result.setWrMin(slDto.getWrMin());
			result.setWrMax(slDto.getWrMax());
			result.setTeMin(slDto.getTeMin());
			result.setTeMax(slDto.getTeMax());
			result.setkMin(slDto.getkMin());
			result.setkMax(slDto.getkMax());
			result.setDefMin(slDto.getDefMin());
			result.setDefMax(slDto.getDefMax());
			result.setIsDefault(slDto.getIsDefault());
			result.setOwnerId(slDto.getOwnerId());
			
		}
		
		return result;
		
	}
	
	private void dtoToEntityForUpdate(SCADLeagueDto slDto, SCADLeague existingEntity) {
		
		if (Objects.nonNull(slDto)) {
			
			if (Objects.nonNull(slDto.getRookieDraftRds()) ) {
				existingEntity.setRookieDraftRds(slDto.getRookieDraftRds());
			}
			if (Objects.nonNull(slDto.getRookieDraftStrategy()) ) {
				existingEntity.setRookieDraftStrategy(slDto.getRookieDraftStrategy());
			}
			if (Objects.nonNull(slDto.getRookieWageScale()) ) {
				existingEntity.setRookieWageScale(slDto.getRookieWageScale());
			}			
			if (Objects.nonNull(slDto.getTeamSalaryCap()) ) {
				existingEntity.setTeamSalaryCap(slDto.getTeamSalaryCap());
			}			
			if (Objects.nonNull(slDto.getLeagueSalaryCap()) ) {
				existingEntity.setLeagueSalaryCap(slDto.getLeagueSalaryCap());
			}
			if (Objects.nonNull(slDto.getSalaryCapExemptionLimit()) ) {
				existingEntity.setSalaryCapExemptionLimit(slDto.getSalaryCapExemptionLimit());
			}
			if (Objects.nonNull(slDto.getIrReliefPerc()) ) {
				existingEntity.setIrReliefPerc(slDto.getIrReliefPerc());
			}
			if (Objects.nonNull(slDto.getFranchiseTagReliefPerc()) ) {
				existingEntity.setFranchiseTagReliefPerc(slDto.getFranchiseTagReliefPerc());
			}			
			if (Objects.nonNull(slDto.getFranchiseTagSpots()) ) {
				existingEntity.setFranchiseTagSpots(slDto.getFranchiseTagSpots());
			}			
			if (Objects.nonNull(slDto.getTradingDraftPickYears()) ) {
				existingEntity.setTradingDraftPickYears(slDto.getTradingDraftPickYears());
			}			
			if (Objects.nonNull(slDto.getQbMin()) ) {
				existingEntity.setQbMin(slDto.getQbMin());
			}			
			if (Objects.nonNull(slDto.getQbMax()) ) {
				existingEntity.setQbMax(slDto.getQbMax());
			}			
			if (Objects.nonNull(slDto.getRbMin()) ) {
				existingEntity.setRbMin(slDto.getRbMin());
			}			
			if (Objects.nonNull(slDto.getRbMax()) ) {
				existingEntity.setRbMax(slDto.getRbMax());
			}	
			if (Objects.nonNull(slDto.getWrMin()) ) {
				existingEntity.setWrMin(slDto.getWrMin());
			}			
			if (Objects.nonNull(slDto.getWrMax()) ) {
				existingEntity.setWrMax(slDto.getWrMax());
			}			
			if (Objects.nonNull(slDto.getTeMin()) ) {
				existingEntity.setTeMin(slDto.getTeMin());
			}			
			if (Objects.nonNull(slDto.getTeMax()) ) {
				existingEntity.setTeMax(slDto.getTeMax());
			}			
			if (Objects.nonNull(slDto.getkMin()) ) {
				existingEntity.setkMin(slDto.getkMin());
			}
			if (Objects.nonNull(slDto.getkMax()) ) {
				existingEntity.setkMax(slDto.getkMax());
			}
			if (Objects.nonNull(slDto.getDefMin()) ) {
				existingEntity.setDefMin(slDto.getDefMin());
			}
			if (Objects.nonNull(slDto.getDefMax()) ) {
				existingEntity.setDefMax(slDto.getDefMax());
			}
			if (Objects.nonNull(slDto.getIsDefault()) ) {
				existingEntity.setIsDefault(slDto.getIsDefault());
			}
			
		}

	}

}
