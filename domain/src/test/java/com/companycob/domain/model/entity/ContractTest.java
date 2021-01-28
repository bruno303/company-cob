package com.companycob.domain.model.entity;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ContractTest {

	@Test
	public void testContractEqualsDifferentId() {
		var contract = new Contract();
		contract.setId(1L);

		var contract2 = new Contract();
		contract.setId(2L);
		assertThat(contract.equals(contract2)).isFalse();
	}

	@Test
	public void testContractEqualsSameId() {
		var contract = new Contract();
		contract.setId(1L);

		var contract2 = new Contract();
		contract.setId(1L);
		assertThat(contract.equals(contract2)).isFalse();
	}

	@Test
	public void testContractGetKeyWithEmptyNumber() {
		var contract = new Contract();
		String key = contract.getKey();

		assertThat(key).isEqualTo("contract:null");
	}

	@Test
	public void testContractGetKeyWithNumberFilled() {
		var contract = new Contract();
		contract.setContractNumber("123456");

		String key = contract.getKey();

		assertThat(key).isEqualTo("contract:123456");
	}

	@Test
	public void testGetEmptyQuotasShouldBeEmptyList() {
		var contract = new Contract();

		List<Quota> quotas = contract.getQuotas();

		assertThat(quotas).isNotNull();
		assertThat(quotas.size()).isEqualTo(0);
	}
}
