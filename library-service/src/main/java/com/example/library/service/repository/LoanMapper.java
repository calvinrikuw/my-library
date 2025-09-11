package com.example.library.service.repository;

import com.example.library.core.dto.LoanBookDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface LoanMapper {

    /**
     * 选择可用的图书库存
     *
     */
    Integer pickAvailableInventory(@Param("bookId") Integer bookId);

    /**
     * 更新图书库存状态为 Borrowed
     *
     */
    int markInventoryBorrowed(@Param("inventoryId") Integer inventoryId,
                              @Param("updatedBy") String userName);

    /**
     * 记录图书借阅信息和借阅日期
     *
     */
    int insertBookLoan(LoanBookDTO loan);

    /**
     * 记录借阅图书的归还日期
     *
     */
    int markBookLoanReturned(@Param("loanId") Integer loanId,
                             @Param("returnDate") LocalDate returnDate,
                             @Param("updatedBy") String userName);

    /**
     * 更新图书库存状态为 AVAILABLE
     *
     */
    int markInventoryAvailable(@Param("inventoryId") Integer inventoryId,
                               @Param("updatedBy") String userName);

    /**
     * 查找借阅中的库存信息
     *
     */
    Integer findLoanBookInventoryId(@Param("userId") Integer userId,
                                    @Param("loanId") Integer loanId);

    /**
     * 查找当前用户的借阅历史
     *
     */
    List<LoanBookDTO> findMyLoanBooks(@Param("userId") int userId);
}
