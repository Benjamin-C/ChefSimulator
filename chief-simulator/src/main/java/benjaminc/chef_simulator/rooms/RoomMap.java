package benjaminc.chef_simulator.rooms;

import java.util.List;

import benjaminc.chef_simulator.Score;
import benjaminc.chef_simulator.control.Chef;

public interface RoomMap {

	public Room getRoom(List<Chef> cooks, Score score);
}
