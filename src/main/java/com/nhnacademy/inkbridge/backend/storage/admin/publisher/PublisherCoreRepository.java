package com.nhnacademy.inkbridge.backend.storage.admin.publisher;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.nhnacademy.inkbridge.backend.domain.BusinessException;
import com.nhnacademy.inkbridge.backend.domain.DomainStatus;
import com.nhnacademy.inkbridge.backend.domain.ErrorMessage;
import com.nhnacademy.inkbridge.backend.domain.admin.publisher.Publisher;
import com.nhnacademy.inkbridge.backend.domain.admin.publisher.PublisherRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PublisherCoreRepository implements PublisherRepository {

	private final PublisherJpaRepository publisherJpaRepository;

	@Override
	public Integer save(Publisher publisher) {
		try {
			PublisherEntity publisherEntity = publisherJpaRepository.save(
				new PublisherEntity(publisher.getName(), DomainStatus.ACTIVE));
			return publisherEntity.getId();
		} catch (DataIntegrityViolationException e) {
			throw new BusinessException(ErrorMessage.PUBLISHER_DUPLICATED);
		}
	}

	@Override
	public boolean existsByName(String name) {
		return publisherJpaRepository.existsByName(name);
	}

	@Override
	public void update(Integer publisherId, Publisher publisher) {
		PublisherEntity publisherEntity = publisherJpaRepository.findById(publisherId)
			.orElseThrow(() -> new BusinessException(ErrorMessage.PUBLISHER_NOT_EXISTS));
		publisherEntity.update(publisher);
	}

	@Override
	public void delete(Integer publisherId) {
		PublisherEntity publisherEntity = publisherJpaRepository.findById(publisherId)
			.orElseThrow(() -> new BusinessException(ErrorMessage.PUBLISHER_NOT_EXISTS));
		publisherEntity.delete();
	}
}
