import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICliente } from 'app/shared/model/cliente.model';
import { getEntities as getClientes } from 'app/entities/cliente/cliente.reducer';
import { IFactura } from 'app/shared/model/factura.model';
import { Pago } from 'app/shared/model/enumerations/pago.model';
import { getEntity, updateEntity, createEntity, reset } from './factura.reducer';

export const FacturaUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const clientes = useAppSelector(state => state.cliente.entities);
  const facturaEntity = useAppSelector(state => state.factura.entity);
  const loading = useAppSelector(state => state.factura.loading);
  const updating = useAppSelector(state => state.factura.updating);
  const updateSuccess = useAppSelector(state => state.factura.updateSuccess);
  const pagoValues = Object.keys(Pago);
  const handleClose = () => {
    props.history.push('/factura' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getClientes({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.fechaFactura = convertDateTimeToServer(values.fechaFactura);
    values.fechaVencimiento = convertDateTimeToServer(values.fechaVencimiento);

    const entity = {
      ...facturaEntity,
      ...values,
      cliente: clientes.find(it => it.id.toString() === values.cliente.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          fechaFactura: displayDefaultDateTime(),
          fechaVencimiento: displayDefaultDateTime(),
        }
      : {
          tipoPago: 'COUNTED',
          ...facturaEntity,
          fechaFactura: convertDateTimeFromServer(facturaEntity.fechaFactura),
          fechaVencimiento: convertDateTimeFromServer(facturaEntity.fechaVencimiento),
          cliente: facturaEntity?.cliente?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="macaviApp.factura.home.createOrEditLabel" data-cy="FacturaCreateUpdateHeading">
            <Translate contentKey="macaviApp.factura.home.createOrEditLabel">Create or edit a Factura</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="factura-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('macaviApp.factura.descripcion')}
                id="factura-descripcion"
                name="descripcion"
                data-cy="descripcion"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 200, message: translate('entity.validation.maxlength', { max: 200 }) },
                }}
              />
              <ValidatedField
                label={translate('macaviApp.factura.fechaFactura')}
                id="factura-fechaFactura"
                name="fechaFactura"
                data-cy="fechaFactura"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('macaviApp.factura.fechaVencimiento')}
                id="factura-fechaVencimiento"
                name="fechaVencimiento"
                data-cy="fechaVencimiento"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('macaviApp.factura.tipoPago')}
                id="factura-tipoPago"
                name="tipoPago"
                data-cy="tipoPago"
                type="select"
              >
                {pagoValues.map(pago => (
                  <option value={pago} key={pago}>
                    {translate('macaviApp.Pago.' + pago)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('macaviApp.factura.totalFactura')}
                id="factura-totalFactura"
                name="totalFactura"
                data-cy="totalFactura"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="factura-cliente"
                name="cliente"
                data-cy="cliente"
                label={translate('macaviApp.factura.cliente')}
                type="select"
                required
              >
                <option value="" key="0" />
                {clientes
                  ? clientes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/factura" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default FacturaUpdate;
