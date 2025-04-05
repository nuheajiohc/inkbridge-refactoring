package com.nhnacademy.inkbridge.backend.domain.admin.publisher;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nhnacademy.inkbridge.backend.domain.BusinessException;
import com.nhnacademy.inkbridge.backend.domain.DomainStatus;
import com.nhnacademy.inkbridge.backend.domain.ErrorMessage;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PublisherAdminService {

	private final PublisherRepository publisherRepository;

	public Integer create(Publisher publisher) {
		if (publisherRepository.existsByName(publisher.getName())) {
			throw new BusinessException(ErrorMessage.PUBLISHER_DUPLICATED);
		}
		return publisherRepository.save(publisher);
	}

	public void update(Integer publisherId, Publisher publisher) {
		publisherRepository.update(publisherId, publisher);
	}

	public void delete(Integer publisherId) {
		publisherRepository.delete(publisherId);
	}

	@Transactional(readOnly = true)
	public Publisher getPublisher(Integer publishId) {
		return publisherRepository.findById(publishId);
	}

	@Transactional(readOnly = true)
	public Page<Publisher> getPublishers(DomainStatus status, Integer page, Integer size) {
		return publisherRepository.findAllByPage(status, page, size);
	}
}
