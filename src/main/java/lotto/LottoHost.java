package lotto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import camp.nextstep.edu.missionutils.Randoms;

public class LottoHost {
	Lotto winningNumberLotto;
	int bonusNumber;

	LottoHost() {

	}

	LottoHost(Lotto winningNumberLotto, int bonusNumber) {
		this.winningNumberLotto = winningNumberLotto;
		this.bonusNumber = bonusNumber;
	}

	List<Integer> makeRandomLottoNumber() {
		return Randoms.pickUniqueNumbersInRange(1, 45, 6);
	}

	List<Integer> compareLottoNumber(List<Lotto> lottoList) {
		List<Integer> resultList = Arrays.asList(0, 0, 0, 0, 0, 0);
		for(Lotto lotto : lottoList) {
			resultList.set(getRank(countSameNumber(lotto)),
				resultList.get(getRank(countSameNumber(lotto))) + 1);
		}
		return resultList;
	}

	int countSameNumber(Lotto lotto) {
		int cnt = 0;
		for(Integer num : lotto.getNumbers()) {
			if(winningNumberLotto.getNumbers().contains(num)) {
				cnt++;
			}
			if(bonusNumber == num) {
				cnt += 11;
			}
		}

		return cnt;
	}

	int getRank(int cnt) {
		if(cnt%10 == 6) {
			return 4;
		}
		if(cnt == 5) {
			return 3;
		}
		if(cnt%10 == 5) {
			return 2;
		}
		if(cnt%10 == 4) {
			return 1;
		}
		if(cnt%10 == 3) {
			return 0;
		}
		return 5;
	}
}
