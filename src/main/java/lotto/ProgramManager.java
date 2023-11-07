package lotto;

import java.util.ArrayList;
import java.util.List;

public class ProgramManager {
	InputView inputView;
	OutputView outputView;
	ProgramManager() {
		this.inputView = new InputView();
		this.outputView = new OutputView();
	}

	/*
	 * 로또 프로그램 시작.
	 * 구매급액 입력 > 구매내역 출력 > 당첨번호/보너스번호 입력 > 결과출력
	 */
	void startProgram() {
		int price = getPrice();

		showAmount(price);
		List<Lotto> lottoList = showBuyLotto(price);
		Customer customer = new Customer(price, lottoList);

		Lotto winningNumberLotto = getLottoNumber();
		int bonusNumber = getBonusNumber(winningNumberLotto);
		setLottoHostInstance(winningNumberLotto, bonusNumber);

		showResult(LottoHost.getInstance(), customer);
	}

	int getPrice() {
		return inputView.getPrice();
	}

	void showAmount(int price) {
		outputView.showAmount(price/1000);
	}

	List<Lotto> showBuyLotto(int price) {
		List<Lotto> lottoList = new ArrayList<>();
		LottoHost lottoHost = LottoHost.getInstance();
		int numberOfBuyingLotto = price/1000;

		while (numberOfBuyingLotto > 0) {
			List<Integer> numberList = lottoHost.makeRandomLottoNumber();
			Lotto lotto = new Lotto(numberList);
			lottoList.add(lotto);
			numberOfBuyingLotto--;
		}

		outputView.showBuyLottoNumber(lottoList);

		return lottoList;
	}

	Lotto getLottoNumber() {
		String number = inputView.getLottoNumber();
		String[] numberArray = number.split(",");
		List<Integer> numberList = new ArrayList<>();
		for(String num : numberArray) {
			numberList.add(Integer.parseInt(num));
		}
		Lotto lotto = new Lotto(numberList);
		return lotto;
	}

	int getBonusNumber(Lotto winningLotto) {
		int bonusNumber = inputView.getBonusNumber(winningLotto);
		return bonusNumber;
	}

	void setLottoHostInstance(Lotto winningNumberLotto, int bonusNumber) {
		LottoHost.getInstance().setWinningNumberLotto(winningNumberLotto);
		LottoHost.getInstance().setBonusNumber(bonusNumber);
	}

	void showResult(LottoHost lottoHost, Customer customer) {
		List<Integer> resultList = compareLotte(lottoHost, customer);

		outputView.printResult(resultList);
		outputView.printRate(calcRate(resultList, customer));
	}

	List<Integer> compareLotte(LottoHost lottoHost, Customer customer) {
		List<Integer> resultList = lottoHost.compareLottoNumber(customer.getLottoList());
		return resultList;
	}

	double calcRate(List<Integer> resultList, Customer customer) {
		int prizeMoney = calcPrizeMoney(resultList);
		int buyMoney = customer.getPrice();

		return ((double)prizeMoney / (double)buyMoney) * 100;
	}

	int calcPrizeMoney(List<Integer> resultList) {
		return resultList.get(0) * 5000
			 + resultList.get(1) * 50000
			 + resultList.get(2) * 1500000
			 + resultList.get(3) * 30000000
			 + resultList.get(4) * 2000000000;
	}
}
