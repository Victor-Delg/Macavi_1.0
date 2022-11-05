import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './producto.reducer';

export const ProductoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const productoEntity = useAppSelector(state => state.producto.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productoDetailsHeading">
          <Translate contentKey="macaviApp.producto.detail.title">Producto</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{productoEntity.id}</dd>
          <dt>
            <span id="talla">
              <Translate contentKey="macaviApp.producto.talla">Talla</Translate>
            </span>
          </dt>
          <dd>{productoEntity.talla}</dd>
          <dt>
            <span id="porcentajeIva">
              <Translate contentKey="macaviApp.producto.porcentajeIva">Porcentaje Iva</Translate>
            </span>
          </dt>
          <dd>{productoEntity.porcentajeIva}</dd>
          <dt>
            <span id="marca">
              <Translate contentKey="macaviApp.producto.marca">Marca</Translate>
            </span>
          </dt>
          <dd>{productoEntity.marca}</dd>
          <dt>
            <span id="genero">
              <Translate contentKey="macaviApp.producto.genero">Genero</Translate>
            </span>
          </dt>
          <dd>{productoEntity.genero}</dd>
          <dt>
            <span id="estilo">
              <Translate contentKey="macaviApp.producto.estilo">Estilo</Translate>
            </span>
          </dt>
          <dd>{productoEntity.estilo}</dd>
          <dt>
            <span id="descripcionProducto">
              <Translate contentKey="macaviApp.producto.descripcionProducto">Descripcion Producto</Translate>
            </span>
          </dt>
          <dd>{productoEntity.descripcionProducto}</dd>
          <dt>
            <span id="cantidadProducto">
              <Translate contentKey="macaviApp.producto.cantidadProducto">Cantidad Producto</Translate>
            </span>
          </dt>
          <dd>{productoEntity.cantidadProducto}</dd>
        </dl>
        <Button tag={Link} to="/producto" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/producto/${productoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProductoDetail;
