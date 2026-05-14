INSERT INTO tenants (nome, slug, endereco)
VALUES ('Lanchonete do Ramon', 'lanchonete-ramon', 'Rio de Janeiro')
    RETURNING id;