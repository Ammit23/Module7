package com.epam.esm.controller;

import com.epam.esm.controller.config.language.Translator;
import com.epam.esm.controller.hateoas.HateoasAdder;
import com.epam.esm.controller.hateoas.impl.CertificateHateoasImpl;
import com.epam.esm.dao.entity.SearchCertificateRequest;
import com.epam.esm.service.dto.entity.CertificateDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.impl.CertificateServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RequestMapping("/certificates")
@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class CertificateController {
    private final HateoasAdder<CertificateDto> hateoasAdder;
    private final CertificateServiceImpl certificateService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CertificateDto> createCertificate(@RequestBody @Valid CertificateDto certificateDto) throws ServiceException {
        CertificateDto giftCertificateDto = certificateService.create(certificateDto);
        hateoasAdder.addLinks(giftCertificateDto);
        return new ResponseEntity<>(giftCertificateDto, HttpStatus.CREATED);
    }

    @GetMapping
    public List<CertificateDto> getCertificates(@ModelAttribute @Valid SearchCertificateRequest searchRequest, BindingResult bindingResult,
                                                          @RequestParam(value = "page", defaultValue = "1", required = false)  int page, @RequestParam(value = "pageSize",
                                                            defaultValue = "20", required = false) int pageSize) {
        if (bindingResult.hasErrors()) {
            //FIXME
            throw new ServiceException("messageCode11");
        }
        List<CertificateDto> certificateDtoList = certificateService.findCertificates(searchRequest, page, pageSize);
        for (CertificateDto dto : certificateDtoList) {
            hateoasAdder.addLinks(dto);
        }
        return certificateDtoList;
    }
//    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
   /* public ResponseEntity<CollectionModel<CertificateDto>> findGiftCertificatesByAnyParams(
            @RequestParam(value = "tag", required = false) String[] tagNames,
            @RequestParam(value = "partName", required = false) String partName,
            @RequestParam(value = "sort_by", defaultValue = "id") String[] sort,
            @RequestParam(value = "sort_direction", defaultValue = "ASC") String sortDirection,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer size) {
        List<CertificateDto> list = certificateService.findCertificatesByAnyParams(tagNames, partName, sort,
               sortDirection, skipQuantity(page, size), size);
        for (CertificateDto dto : list) {
            hateoasAdder.addLinks(dto);
        }
        return CertificateHateoasImpl.getCollectionModelWithPagination(tagNames, partName, sort, sortDirection, page, size, list);
    }*/

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<CertificateDto> findCertificate(
            @PathVariable Integer id) throws ServiceException {
        CertificateDto giftCertificateDto = certificateService.find(id);
        hateoasAdder.addLinks(giftCertificateDto);
        return new ResponseEntity<>(giftCertificateDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteCertificate(@PathVariable Integer id) throws ServiceException {
        certificateService.delete(id);
        return new ResponseEntity<>(Translator.toLocale("certificate.deleted") + id, HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CertificateDto> updateCertificatePartly(
            @PathVariable Integer id, @RequestBody Map<String, Object> updates) throws ServiceException {
        CertificateDto giftCertificateDto = certificateService.update(id, updates);
        hateoasAdder.addLinks(giftCertificateDto);
        return new ResponseEntity<>(giftCertificateDto, HttpStatus.OK);
    }

    @PostMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CertificateDto> updateCertificate(
            @PathVariable Integer id,
            @RequestBody @Valid CertificateDto certificateDto) throws ServiceException {
        CertificateDto giftCertificateDto = certificateService.update(id, certificateDto);
        hateoasAdder.addLinks(giftCertificateDto);
        return new ResponseEntity<>(giftCertificateDto, HttpStatus.OK);
    }

    private int skipQuantity(int page, int size) {
        int skip = 0;
        if (page > 0) {
            skip = (page - 1) * size;
        }
        return skip;
    }
}
