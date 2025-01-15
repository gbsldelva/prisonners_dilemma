import React, { useState } from 'react';
import { Dialog, DialogTitle, DialogContent, DialogActions, Button, TextField } from '@mui/material';

const NombreDesPartiesDialog = ({ open, onClose, onSubmit, nombreDesParties, setNombreDesParties }) => {

  const handleSubmit = () => {
    if (!nombreDesParties || isNaN(nombreDesParties) || nombreDesParties <= 0) {
      alert('Veuillez entrer un nombre valide.');
      return;
    }
    onSubmit(parseInt(nombreDesParties, 10));
    setNombreDesParties(''); 
  };

  return (
    <Dialog open={open} onClose={onClose}>
      <DialogTitle>Nombre des Parties</DialogTitle>
      <DialogContent>
        <TextField
          autoFocus
          margin="dense"
          label="Nombre des Parties"
          type="number"
          fullWidth
          value={nombreDesParties}
          onChange={(e) => setNombreDesParties(parseInt(e.target.value))}
        />
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose} color="secondary">
          Annuler
        </Button>
        <Button onClick={handleSubmit} color="primary">
          Confirmer
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default NombreDesPartiesDialog;
