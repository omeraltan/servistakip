import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import ServisIsUpdatePage from './servis-is-update.page-object';

const expect = chai.expect;
export class ServisIsDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('servisTakipApp.servisIs.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-servisIs'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class ServisIsComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('servis-is-heading'));
  noRecords: ElementFinder = element(by.css('#app-view-container .table-responsive div.alert.alert-warning'));
  table: ElementFinder = element(by.css('#app-view-container div.table-responsive > table'));

  records: ElementArrayFinder = this.table.all(by.css('tbody tr'));

  getDetailsButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-info.btn-sm'));
  }

  getEditButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-primary.btn-sm'));
  }

  getDeleteButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-danger.btn-sm'));
  }

  async goToPage(navBarPage: NavBarPage) {
    await navBarPage.getEntityPage('servis-is');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateServisIs() {
    await this.createButton.click();
    return new ServisIsUpdatePage();
  }

  async deleteServisIs() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const servisIsDeleteDialog = new ServisIsDeleteDialog();
    await waitUntilDisplayed(servisIsDeleteDialog.deleteModal);
    expect(await servisIsDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/servisTakipApp.servisIs.delete.question/);
    await servisIsDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(servisIsDeleteDialog.deleteModal);

    expect(await isVisible(servisIsDeleteDialog.deleteModal)).to.be.false;
  }
}
