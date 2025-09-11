package com.example.library.service.service;

import com.example.library.core.dto.LoanBookDTO;
import com.example.library.core.exception.BusinessException;
import com.example.library.service.repository.LoanMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {

    private final LoanMapper loanMapper;

    public LoanService(LoanMapper loanMapper) {
        this.loanMapper = loanMapper;
    }

    @Transactional
    public void borrow(Integer userId, Integer bookId, String userName) {
        if (userId == null || bookId == null) {
            throw new BusinessException("INVALID_ARGUMENT", "用户或图书ID不能为空");
        }

        Integer bookInventoryId = loanMapper.pickAvailableInventory(bookId);
        if (bookInventoryId == null) {
            throw new BusinessException("NO_INVENTORY", "当前图书没有库存");
        }

        int updated = loanMapper.markInventoryBorrowed(bookInventoryId, userName);
        if (updated != 1) {
            throw new BusinessException("CONCURRENT_BORROW", "该图书已被他人借走");
        }

        LoanBookDTO loanBook = new LoanBookDTO(0, userId, bookInventoryId, LocalDate.now(), userName);
        loanMapper.insertBookLoan(loanBook);
    }

    @Transactional
    public void returnBook(Integer userId, Integer loanId, String userName) {
        if (userId == null || loanId == null) {
            throw new BusinessException("INVALID_ARGUMENT", "用户或借阅ID不能为空");
        }

        Integer inventoryId = loanMapper.findLoanBookInventoryId(userId, loanId);
        if (inventoryId == null) {
            throw new BusinessException("NOT_FOUND", "借阅记录不存在或已归还");
        }

        int updated = loanMapper.markBookLoanReturned(loanId, LocalDate.now(), userName);
        if (updated != 1) {
            throw new BusinessException("RETURN_FAIL", "图书归还失败");
        }

        loanMapper.markInventoryAvailable(inventoryId, userName);
    }

    public List<LoanBookDTO> findMyLoanBooks(Integer userId) {
        if (userId == null) {
            throw new BusinessException("INVALID_ARGUMENT", "用户ID不能为空");
        }
        return loanMapper.findMyLoanBooks(userId);
    }

}
