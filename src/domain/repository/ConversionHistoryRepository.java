package domain.repository;

import java.util.List;

import domain.entity.Money;

public interface ConversionHistoryRepository {

	List<String> getLast(int n);

	void save(Money from, Money to);
    
}