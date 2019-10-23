package benjaminc.chef_simulator.rooms;

import java.util.List;

import benjaminc.chef_simulator.Score;
import benjaminc.chef_simulator.control.Cook;

public interface RoomMap {

	public Room getRoom(List<Cook> cooks, Score score);
}
