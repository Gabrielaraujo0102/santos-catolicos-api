package com.example.santoscatolicosapitrabalho.repository;

import com.example.santoscatolicosapitrabalho.model.Santo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SantoRepository extends JpaRepository<Santo, Long> {

    // Cada filtro é opcional: se o parâmetro vier nulo, a condição é ignorada.
    // O CAST evita o erro "could not determine data type of parameter" do PostgreSQL.
    @Query("SELECT s FROM Santo s WHERE " +
            "(CAST(:nome AS string) IS NULL OR LOWER(s.nome) LIKE LOWER(CONCAT('%', CAST(:nome AS string), '%'))) AND " +
            "(CAST(:pais AS string) IS NULL OR LOWER(s.pais) = LOWER(CAST(:pais AS string))) AND " +
            "(CAST(:seculo AS integer) IS NULL OR s.seculo = :seculo) AND " +
            "(CAST(:canonizado AS boolean) IS NULL OR s.canonizado = :canonizado)")
    List<Santo> filtrar(@Param("nome") String nome,
                        @Param("pais") String pais,
                        @Param("seculo") Integer seculo,
                        @Param("canonizado") Boolean canonizado);
}
