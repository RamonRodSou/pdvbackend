INSERT INTO tenants (nome, slug, endereco, cor_principal, cor_secundario)
VALUES ('Lanchonete do Ramon', 'lanchonete-ramon', 'Rio de Janeiro', '#F97316', '#B91C1C')
    RETURNING id;